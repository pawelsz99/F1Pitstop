package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A fragment representing a list of Items.
 */
class ResultFragment : Fragment() {

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


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_list, container, false)

        populateResultList()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ResultRecyclerViewAdapter(resultList)
            }
        }
        return view
    }

    private fun populateResultList() {
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

        // if number of pit stops is equal
        if (driver1PitStops.stop!!.size == driver2PitStops.stop!!.size) {
            // difference
            resultList.add(ResultData("Difference"))
            resultList.add(ResultData("Stop", "", "Duration"))
            var totalTime = 0.0
            for (i in 0 until driver1PitStops.stop!!.size) {
                resultList.add(
                    ResultData(
                        driver1PitStops.stop!![i],
                        "${driver1PitStops.lap!![i]} / ${driver2PitStops.lap!![i]}",
                        "%.3f".format(driver1PitStops.duration!![i].toFloat() - driver2PitStops.duration!![i].toFloat())
                    )
                )
                totalTime += driver1PitStops.duration!![i].toFloat() - driver2PitStops.duration!![i].toFloat()
            }
            // total difference
            resultList.add(ResultData("Total", "", "%.3f".format(totalTime)))

        } else { // if one of the drivers has more pit stops
            // display the average time of pit stops
            resultList.add(ResultData("Average pit stop time"))

            var driver1PitStopsSumTime = 0.0
            for (num in driver1PitStops.duration!!) {
                driver1PitStopsSumTime += num.toFloat()
            }
            val driver1PitStopsAverageTime =
                driver1PitStopsSumTime / driver1PitStops.duration!!.size
            resultList.add(ResultData(driver1Name!!, "", "%.3f".format(driver1PitStopsAverageTime)))

            var driver2PitStopsSumTime = 0.0
            for (num in driver2PitStops.duration!!) {
                driver2PitStopsSumTime += num.toFloat()
            }
            val driver2PitStopsAverageTime =
                driver2PitStopsSumTime / driver2PitStops.duration!!.size
            resultList.add(ResultData(driver2Name!!, "", "%.3f".format(driver2PitStopsAverageTime)))

            // compare the difference
            resultList.add(
                ResultData(
                    "Difference",
                    "",
                    "%.3f".format(driver1PitStopsAverageTime - driver2PitStopsAverageTime)
                )
            )

        }


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

//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//            ResultFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
//            }
    }
}