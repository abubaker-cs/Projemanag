package org.abubaker.projemanag.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityTaskListBinding

class TaskListActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@TaskListActivity, R.layout.activity_task_list)


    }


}