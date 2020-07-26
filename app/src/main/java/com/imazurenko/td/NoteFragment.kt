package com.imazurenko.td

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.imazurenko.td.databinding.NoteFragmentBinding
import javax.inject.Inject

class NoteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TdApplication).appComponent.inject(this)
    }

    val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: NoteFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.note_fragment, container, false
        )

        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this, viewModelFactory)[NoteFragmentViewModel::class.java]

        viewModel.init(args.uid)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.MainActivityToolbarCreateItem).isVisible = false
        menu.findItem(R.id.MainActivityToolbarDeleteItem).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }
}
