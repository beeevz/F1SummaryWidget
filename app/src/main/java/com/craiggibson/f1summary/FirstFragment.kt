package com.craiggibson.f1summary

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.craiggibson.f1summary.databinding.FragmentFirstBinding
import com.craiggibson.f1summary.widget.F1TrackSummaryWidget


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        _binding?.updateWidget?.setOnClickListener {
            val intent = createIntentForExecutingWidget()
            activity?.sendBroadcast(intent)
        }
        return binding.root
    }

    private fun createIntentForExecutingWidget(): Intent {
        val intent = Intent(activity, F1TrackSummaryWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids: IntArray = AppWidgetManager.getInstance(activity?.application)
            .getAppWidgetIds(
                ComponentName(activity?.application!!, F1TrackSummaryWidget::class.java)
            )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        return intent
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}