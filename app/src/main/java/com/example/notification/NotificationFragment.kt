package com.example.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.notification.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val channelId = "my_notification_channel"
    private lateinit var notificationManager: NotificationManager

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission.launch(Manifest.permission.POST_NOTIFICATIONS)

        notificationManager =
            requireContext().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        binding.button.setOnClickListener {
            createNotificationChanel()
            sendNotification()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun createNotificationChanel() {
        val channel = NotificationChannel(
            channelId,
            "my_channel_name",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Notification title")
            .setContentText("This is a notification")
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }
}