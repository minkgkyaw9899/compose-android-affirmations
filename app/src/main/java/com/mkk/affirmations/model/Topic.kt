package com.mkk.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
  @StringRes
  val topicName: Int,
  val numberOfCourses: Int,
  @DrawableRes val imageResourceId: Int
)
