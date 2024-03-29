package com.inasweaterpoorlyknit.scenes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.inasweaterpoorlyknit.scenes.graphics.RotationSensorHelper
import com.inasweaterpoorlyknit.scenes.graphics.SceneSurfaceView
import com.inasweaterpoorlyknit.scenes.repositories.UserPreferencesDataStoreRepository
import com.inasweaterpoorlyknit.scenes.viewmodels.SceneListDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SceneFragment: Fragment(), MavericksView {

  private val viewModel: SceneListDetailViewModel by activityViewModel()

  @Inject
  lateinit var userPreferencesRepo: UserPreferencesDataStoreRepository
  @Inject
  lateinit var rotationSensorHelper: RotationSensorHelper

  private lateinit var sceneSurfaceView: SceneSurfaceView

  override fun onAttach(context: Context) {
    super.onAttach(context)
    requireActivity().hideSystemUI()
  }

  override fun onDetach() {
    super.onDetach()
    requireActivity().showSystemUI()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    rotationSensorHelper.reset()
    val context = requireContext()
    sceneSurfaceView = withState(viewModel){ state ->
      SceneSurfaceView(context, state.sceneCreator(context, userPreferencesRepo, rotationSensorHelper))
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return sceneSurfaceView
  }

  override fun onPause() {
    super.onPause()
    sceneSurfaceView.onPause()
  }

  override fun onResume() {
    super.onResume()
    sceneSurfaceView.onResume()
  }

  override fun invalidate() {}
}