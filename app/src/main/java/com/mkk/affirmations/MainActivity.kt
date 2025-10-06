package com.mkk.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mkk.affirmations.data.Datasource
import com.mkk.affirmations.model.Affirmation
import com.mkk.affirmations.model.Topic
import com.mkk.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AffirmationsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          val padding = innerPadding
          val layoutDirection = LocalLayoutDirection.current
          Surface(
            modifier = Modifier
              .fillMaxSize()
              .statusBarsPadding()
              .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                  .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                  .calculateEndPadding(layoutDirection)
              )
          ) {
//            AffirmationsApp(modifier = Modifier)
            TopicGrid(
              modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_small),
                top = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small),
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun TopicGrid(modifier: Modifier = Modifier) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
    modifier = modifier
  ) {
    items(Datasource().loadTopics()) { topic ->
      TopicCard(topic)
    }
  }
}

@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier) {
  Card {
    Row {
      Box {
        Image(
          painter = painterResource(id = topic.imageResourceId),
          contentDescription = null,
          modifier = modifier
            .size(width = 68.dp, height = 68.dp)
            .aspectRatio(1f),
          contentScale = ContentScale.Crop
        )
      }
      Column {
        Text(
          text = stringResource(id = topic.topicName),
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_medium),
            top = dimensionResource(R.dimen.padding_medium),
            end = dimensionResource(R.dimen.padding_medium),
            bottom = dimensionResource(R.dimen.padding_small)
          )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            painter = painterResource(R.drawable.ic_grain),
            contentDescription = null,
            modifier = Modifier
              .padding(start = dimensionResource(R.dimen.padding_medium))
          )
          Text(
            text = topic.numberOfCourses.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
          )
        }
      }
    }
  }
}

@Composable
fun AffirmationsApp(modifier: Modifier = Modifier) {
  AffirmationList(
    affirmationList = Datasource().loadAffirmations(),
    modifier = modifier
  )
}

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier) {
    itemsIndexed(affirmationList) { index, affirmation ->
      val lastItem = index == affirmationList.lastIndex
      AffirmationCard(
        affirmation = affirmation,
        modifier = Modifier.padding(
          top = 8.dp,
          start = 8.dp,
          end = 8.dp,
          bottom = if (lastItem) 40.dp else 8.dp
        )
      )
    }
  }
}

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
  Card(modifier = modifier) {
    Column {
      Image(
        painter = painterResource(affirmation.imageResourceId),
        contentDescription = stringResource(affirmation.stringResourceId),
        modifier = modifier
          .fillMaxWidth()
          .height(194.dp),
        contentScale = ContentScale.Crop
      )
      Text(
        text = stringResource(affirmation.stringResourceId),
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.headlineSmall
      )
    }
  }
}

@Preview
@Composable
private fun AffirmationCardPreview() {
  AffirmationCard(Affirmation(R.string.affirmation1, R.drawable.image1))
}
