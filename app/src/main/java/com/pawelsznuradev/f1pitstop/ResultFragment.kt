package com.pawelsznuradev.f1pitstop

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Pawel Sznura on 07/04/2021.
 */

/**
 * Result fragment
 *
 * @constructor Create empty Result fragment
 */
class ResultFragment : Fragment() {

    private var season: String? = null
    private var raceName: String? = null
    private var driver1Name: String? = null
    private var driver2Name: String? = null
    lateinit var driver1PitStops: DriverPitStops
    lateinit var driver2PitStops: DriverPitStops

    /**
     * Result list
     * holds the data to be displayed in a understandable format for the [ResultRecyclerViewAdapter]
     */
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

    /**
     * Populate result list
     * putting all the data to be displayed into [resultList]
     */
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
                        "%.3f".format(
                            PitStopTime(driver1PitStops.duration!![i]).getTotalTime() - PitStopTime(
                                driver2PitStops.duration!![i]
                            ).getTotalTime()
                        // "%.3f" used to get only 3 decimal places
                        )
                    )
                )
                totalTime += PitStopTime(driver1PitStops.duration!![i]).getTotalTime() - PitStopTime(
                    driver2PitStops.duration!![i]
                ).getTotalTime()
            }
            // total difference
            resultList.add(ResultData("Total", "", "%.3f".format(totalTime)))

        } else { // if one of the drivers has more pit stops
            // display the average time of pit stops
            resultList.add(ResultData("Average pit stop time"))

            var driver1PitStopsSumTime = 0.0
            for (num in driver1PitStops.duration!!) {
                driver1PitStopsSumTime += PitStopTime(num).getTotalTime()
            }

            // when the driver has no pit stops we cannot divide by 0
            var driver1PitStopsAverageTime = driver1PitStopsSumTime
            if (driver1PitStops.duration!!.size != 0) {
                driver1PitStopsAverageTime =
                    driver1PitStopsSumTime / driver1PitStops.duration!!.size
            }
            resultList.add(ResultData(driver1Name!!, "", "%.3f".format(driver1PitStopsAverageTime)))
            // "%.3f" used to get only 3 decimal places


            var driver2PitStopsSumTime = 0.0
            for (num in driver2PitStops.duration!!) {
                driver2PitStopsSumTime += PitStopTime(num).getTotalTime()
            }

            // when the driver has no pit stops we cannot divide by 0
            var driver2PitStopsAverageTime = driver2PitStopsSumTime
            if (driver2PitStops.duration!!.size != 0) {
                driver2PitStopsAverageTime =
                    driver2PitStopsSumTime / driver2PitStops.duration!!.size
            }
            resultList.add(ResultData(driver2Name!!, "", "%.3f".format(driver2PitStopsAverageTime)))
            // "%.3f" used to get only 3 decimal places


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
        const val ARG_COLUMN_COUNT = "column-count"
        private const val seasonKey = "season"
        private const val raceNameKey = "raceName"
        private const val driver1NameKey = "driver1Name"
        private const val driver2NameKey = "driver2Name"
        private const val driver1PitStopsKey = "driver1PitStop"
        private const val driver2PitStopsKey = "driver2PitStop"

    }
}