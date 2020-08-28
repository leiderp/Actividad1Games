package com.leiderp.MemoryGame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class menu : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.button_game1).setOnClickListener(this)
        view.findViewById<Button>(R.id.button_game2).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.button_game1 ->{
                navController!!.navigate(R.id.action_menu_to_mainActivity)
            }
            R.id.button_game2 ->{
                navController!!.navigate(R.id.action_menu_to_game2)
            }
        }
    }

}