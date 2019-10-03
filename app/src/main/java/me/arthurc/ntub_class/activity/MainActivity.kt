package me.arthurc.ntub_class.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.arthurc.ntub_class.R
import me.arthurc.ntub_class.adapter.ClassScheduleAdapter
import me.arthurc.ntub_class.helper.SPHelper
import me.arthurc.ntub_class.model.ClassScheduleModel
import me.arthurc.ntub_class.network.ClassScheduleClient
import me.arthurc.ntub_class.network.ErrorHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : BaseActivity(), SearchView.OnQueryTextListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val stdId = SPHelper.getStdId(this)
        val data = SPHelper.getClassSchedule(this)

        if (stdId != null && data != null) {
            bindClassData(stdId, data)
            return
        }

        if (stdId != null) {
            getClassSchedule(stdId)
            return
        }

        SPHelper.clearAll(this)
        classSchedule.visibility = View.GONE
        emptyClassSchedule.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            R.id.action_today -> {
                scrollToToday()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextChange(s: String): Boolean {
        return false
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        if (s.isBlank()) {
            return true
        }

        invalidateOptionsMenu()  // Close search view
        getClassSchedule(s)

        return false
    }

    private fun getClassSchedule(stdId: String) {
        val call = ClassScheduleClient.service.getClassSchedule(stdId.trim())

        showLoadingDialog()

        call.enqueue(object : Callback<ClassScheduleModel> {
            override fun onFailure(call: Call<ClassScheduleModel>, t: Throwable) {
                dismissLoadingDialog()
                log(t.message.orEmpty(), "Fail Message")
                t(getString(R.string.request_fail))
            }

            override fun onResponse(call: Call<ClassScheduleModel>, response: Response<ClassScheduleModel>) {
                dismissLoadingDialog()

                if (!response.isSuccessful) {
                    val error = ErrorHandler.parse(response)

                    log(getString(R.string.request_error))
                    t("${getString(R.string.error)}: ${error.message}")

                    return
                }

                bindClassData(stdId, response.body())
                t(R.string.request_finish)
            }
        })
    }

    private fun bindClassData(stdId: String, data: ClassScheduleModel) {
        supportActionBar?.title = "$stdId 的課表"
        classScheduleList.layoutManager = LinearLayoutManager(this)
        classScheduleList.adapter = ClassScheduleAdapter(this, data)

        SPHelper.setStdId(this, stdId)
        SPHelper.setClassSchedule(this, data)

        scrollToToday()

        classSchedule.visibility = View.VISIBLE
        emptyClassSchedule.visibility = View.GONE
    }

    private fun scrollToToday() {
        var dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }

        classScheduleList.scrollToPosition(dayOfWeek - 1)
    }

}
