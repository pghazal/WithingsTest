package com.pghaz.withingstest

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pghaz.withingstest.adapter.IItemClickListener
import com.pghaz.withingstest.adapter.ImageAdapter
import com.pghaz.withingstest.databinding.FragmentSearchBinding
import com.pghaz.withingstest.domain.internal.ImageViewModel
import com.pghaz.withingstest.utils.Arguments
import com.pghaz.withingstest.viewmodel.ISearchView
import com.pghaz.withingstest.viewmodel.SearchViewModel

class SearchFragment : Fragment(), ISearchView, IItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val actionModeCallback: SelectionActionModeCallback = SelectionActionModeCallback()
    private var actionMode: ActionMode? = null

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var validationButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

        /*return ComposeView(requireContext()).apply {
            setContent {
                buildUI(this.context)
            }
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews(binding)
    }

    private fun configureViews(viewBinding: FragmentSearchBinding) {
        imageAdapter = initImageAdapter()
        configureRecyclerView(viewBinding, imageAdapter)
        configureSearchView(viewBinding)
        configureValidationButton(viewBinding)
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.hitsLiveData.observe(this, { hits ->
            imageAdapter.submitList(hits)
        })
    }

    private fun initImageAdapter(): ImageAdapter {
        return ImageAdapter(this)
    }

    private fun configureRecyclerView(
        viewBinding: FragmentSearchBinding,
        imageAdapter: ImageAdapter
    ) {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = imageAdapter
    }

    private fun configureValidationButton(viewBinding: FragmentSearchBinding) {
        validationButton = viewBinding.validationButton
        validationButton.setOnClickListener {
            val selectedUrls = imageAdapter.getSelectedItems()

            val intent = Intent(context, SelectedImagesActivity::class.java)
            intent.putParcelableArrayListExtra(Arguments.ARGS_IMAGE_URL_SELECTED, selectedUrls)

            startActivity(intent)
        }
    }

    private fun configureSearchView(viewBinding: FragmentSearchBinding) {
        val searchView = viewBinding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()

                if (query.isEmpty()) {
                    clearSearch()
                } else {
                    searchImages(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Is it intuitive to remove result from list when text is cleared..?
                if (newText.isNullOrEmpty()) {
                    clearSearch()
                }

                return false
            }
        })
    }

    override fun searchImages(query: String?) {
        searchViewModel.searchImages(query)
    }

    override fun clearSearch() {
        searchViewModel.clearSearch()

        actionMode?.finish()
    }

    override fun onItemClicked(imageViewModel: ImageViewModel, position: Int) {
        if (actionMode != null) {
            toggleSelection(imageViewModel, position)
        } else {
            // TODO: navigate to single item
        }
    }

    override fun onItemLongClicked(imageViewModel: ImageViewModel, position: Int) {
        if (actionMode == null) {
            if (activity is MainActivity) {
                actionMode = (activity as MainActivity).startSupportActionMode(actionModeCallback)
            }
        }

        toggleSelection(imageViewModel, position)
    }

    private fun toggleSelection(imageViewModel: ImageViewModel, position: Int) {
        imageAdapter.toggleSelection(imageViewModel, position)

        actionMode?.let {
            val count: Int = imageAdapter.getSelectedItemCount()

            if (count == 0) {
                it.finish()
            } else {
                it.title = String.format("%d items selected", count)
                it.invalidate()

                updateValidationButtonState()
            }
        }
    }

    private fun updateValidationButtonState() {
        // At least 2 selected items
        if (imageAdapter.getSelectedItemCount() > 1) {
            validationButton.visibility = View.VISIBLE
        } else {
            validationButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class SelectionActionModeCallback : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            imageAdapter.clearSelection()
            actionMode = null

            updateValidationButtonState()
        }
    }
}