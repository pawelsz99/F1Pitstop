package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pawelsznuradev.f1pitstop.dummy.DummyContent


/**
 * A fragment representing a list of Items.
 */
class TestItemFragment : Fragment() {

    private var season: String? = null
    private var raceName: String? = null
    private var driver1Name: String? = null
    private var driver2Name: String? = null
    lateinit var driver1PitStops: DriverPitStops
    lateinit var driver2PitStops: DriverPitStops

    private val resultList = mutableListOf<ResultData>()

    private var columnCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            season = it.getString(seasonKey)
            raceName = it.getString(raceNameKey)
            driver1Name = it.getString(driver1NameKey)
            driver2Name = it.getString(driver2NameKey)
            driver1PitStops = it.getParcelable(driver1PitStopsKey)!!
            driver2PitStops = it.getParcelable(driver2PitStopsKey)!!
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        activity?.title = "$season $raceName"

        // name driver 1
        resultList.add(ResultData(driver1Name!!))
        resultList.add(ResultData("Stop", "Lap", "Duration"))
        for (i in 0 until driver1PitStops.stop!!.size) {
            resultList.add(
                ResultData(
                    driver1PitStops.stop!![i],
                    driver1PitStops.lap!![i],
                    driver1PitStops.duration!![i]
                )
            )
        }

        // name driver 2
        resultList.add(ResultData(driver2Name!!))
        resultList.add(ResultData("Stop", "Lap", "Duration"))
        for (i in 0 until driver2PitStops.stop!!.size) {
            resultList.add(
                ResultData(
                    driver2PitStops.stop!![i],
                    driver2PitStops.lap!![i],
                    driver2PitStops.duration!![i]
                )
            )
        }
        //


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_item_list, container, false)

        // Set the adapter]';dasda;sd
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyTestItemRecyclerViewAdapter(resultList)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        private const val seasonKey = "season"
        private const val raceNameKey = "raceName"
        private const val driver1NameKey = "driver1Name"
        private const val driver2NameKey = "driver2Name"
        private const val driver1PitStopsKey = "driver1PitStop"
        private const val driver2PitStopsKey = "driver2PitStop"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TestItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}