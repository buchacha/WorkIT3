package com.example.workit3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class EmployerFragment : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    var mArrayList: ArrayList<String>? = null
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var mRecyclerAdapter: RecyclerView.Adapter<*>
    lateinit var mTextView: TextView
    lateinit var mButton: Button

    var mRequest: Disposable?=null

    var request: Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater!!.inflate(R.layout.fragment_employer, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextView = view.findViewById<TextView>(R.id.textView)
        mButton = view.findViewById(R.id.button)
        mLayoutManager = LinearLayoutManager(context)

        if (mArrayList == null)
            mArrayList = ArrayList()

        mRecyclerAdapter = MyAdapter(mArrayList!!)

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = mLayoutManager

            adapter = mRecyclerAdapter
        }

        mButton.setOnClickListener {

            val obs = createRequest("http://10.0.2.2:8000/api/employers")
                .map { Gson().fromJson(it, Employer::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

            mRequest = obs.subscribe({
                for (employer in it.employers)
                    mArrayList?.add(employer.name+", "+employer.company)

                mRecyclerAdapter.notifyDataSetChanged()
            }, {
                Log.e("test", "", it)
            })
        }
    }

    override fun onDestroy() {
        request?.dispose()
        super.onDestroy()
    }

    class Employer (
        val employers: ArrayList<EmployerItem>
    )

    class EmployerItem (
        val name: String,
        val company: String
    )
}
