package com.imazurenko.td

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.imazurenko.td.databinding.NoteListFragmentBinding
import javax.inject.Inject

class NoteListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TdApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = NoteListFragmentBinding.inflate(inflater, container, false)

        val viewModel =
            ViewModelProvider(this, viewModelFactory)[NoteListFragmentViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.NoteListFragmentRecyclerView.adapter = NoteListRecyclerViewAdapter(this)
        binding.NoteListFragmentRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        val inputMethodManager =
            this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.view?.windowToken, 0)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.MainActivityToolbarCreateItem).isVisible = true
        menu.findItem(R.id.MainActivityToolbarDeleteItem).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}



