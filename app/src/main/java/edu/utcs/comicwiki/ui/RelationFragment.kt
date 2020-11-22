package edu.utcs.comicwiki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.utcs.comicwiki.R

class RelationFragment : Fragment() {
    // TODO: static var/method
    companion object {
        fun newInstance(): RelationFragment {
            return RelationFragment()
        }
    }

    // TODO: references
    private val viewModel: MainViewModel by activityViewModels()

    // TODO: private vars
    private lateinit var swipe: SwipeRefreshLayout

    // TODO: data observer

    // TODO: action bar interaction

    // TODO: adapter

    // TODO: swipe
    private fun initSwipeLayout(root: View) {
        swipe = root.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipe.setOnRefreshListener {
        }
    }

    private var dX = 0f
    private var dY = 0f

    private fun <K> createSpringAnimation(
        obj: K,
        property: FloatPropertyCompat<K>
    ): SpringAnimation {
        return SpringAnimation(obj, property).setSpring(SpringForce())
    }

    private inline fun SpringAnimation.onUpdate(crossinline onUpdate: (value: Float) -> Unit): SpringAnimation {
        return this.addUpdateListener { _, value, _ ->
            onUpdate(value)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: fragment
        val root = inflater.inflate(R.layout.relation_fragment, container, false)

        // TODO: viewModel observers

        // TODO: UI interactions
        val drag = root.findViewById<TextView>(R.id.drag)
        val first = root.findViewById<TextView>(R.id.first)
        val second = root.findViewById<TextView>(R.id.second)
        val firstXAnim = createSpringAnimation(first, DynamicAnimation.X)
        val firstYAnim = createSpringAnimation(first, DynamicAnimation.Y)
        val secondXAnim = createSpringAnimation(second, DynamicAnimation.X)
        val secondYAnim = createSpringAnimation(second, DynamicAnimation.Y)
        firstXAnim.spring.stiffness = 50f
        firstXAnim.spring.dampingRatio = 0.75f
        firstYAnim.spring.stiffness = 50f
        firstYAnim.spring.dampingRatio = 0.75f



        firstXAnim.onUpdate { value ->
            secondXAnim.animateToFinalPosition(
                value + ((first.width -
                        second.width) / 2)
            )
        }
        firstYAnim.onUpdate { value ->
            secondYAnim.animateToFinalPosition(value + first.height)
        }
        drag.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY

                    view.animate().x(newX).y(newY).setDuration(0).start()
                    firstXAnim.animateToFinalPosition(
                        newX + ((drag.width -
                                first.width) / 2)
                    )
                    firstYAnim.animateToFinalPosition(newY + drag.height)
                }
            }

            return@setOnTouchListener true
        }


        first.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY

                    view.animate().x(newX).y(newY).setDuration(0).start()
                    firstXAnim.animateToFinalPosition(
                        drag.x + 50
                    )
                    firstYAnim.animateToFinalPosition(drag.y + 50)
                }
            }

            return@setOnTouchListener true
        }
        return root
    }
}