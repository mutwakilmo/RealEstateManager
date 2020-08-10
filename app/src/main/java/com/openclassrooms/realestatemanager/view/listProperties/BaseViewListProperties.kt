package com.openclassrooms.realestatemanager.view.listProperties

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.view.mainActivity.MainActivity
import com.openclassrooms.realestatemanager.base.BaseFragmentRealStateManager
import com.openclassrooms.realestatemanager.base.RealStateManagerView
import com.openclassrooms.realestatemanager.view.searchPropertyResult.SearchResultActivity
import com.openclassrooms.realestatemanager.utils.ACTION_TYPE_LIST_PROPERTY
import com.openclassrooms.realestatemanager.utils.Currency
import com.openclassrooms.realestatemanager.utils.showSnackBar
import com.openclassrooms.realestatemanager.viewmodel.ListPropertyViewModel
import com.openclassrooms.realestatemanager.viewstate.ListPropertyViewState
import com.openclassrooms.realestatemanager.viewstate.PropertyListIntent

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
abstract class BaseViewListProperties : BaseFragmentRealStateManager(), RealStateManagerView<ListPropertyViewState> {

    protected lateinit var viewModel: ListPropertyViewModel
    protected var currentCurrency: Currency? = null

    protected abstract fun renderListProperties(properties: List<PropertyWithAllData>, itemSelected: Int?)
    protected abstract fun renderIsLoading(loading: Boolean)
    protected abstract fun renderChangeCurrency(currency: Currency)

    //--------------------
    // VIEW MODEL CONNECTION
    //--------------------

    override fun configureViewModel(){
        val viewModelFactory = Injection.providesViewModelFactory(activity!!.applicationContext)
        viewModel = ViewModelProviders.of(
                this,
                viewModelFactory
        ).get(ListPropertyViewModel::class.java)

        viewModel.viewState.observe(this, Observer { render(it) })

    }

    protected fun configureActionType(){
        val argument = arguments?.getString(ACTION_TYPE_LIST_PROPERTY, ActionTypeList.ALL_PROPERTIES.actionName)
        argument?.let{
            val actionType = ActionTypeList.valueOf(it)
            viewModel.actionFromIntent(PropertyListIntent.SetActionTypeIntent(actionType))
            updatePropertiesDisplay()
        }

    }

    fun updatePropertiesDisplay(){
        viewModel.actionFromIntent(PropertyListIntent.DisplayPropertiesIntent)
    }


    override fun render(stateProperty: ListPropertyViewState?) {
        if (stateProperty == null) return

        if (stateProperty.openDetails) {
            renderOpenPropertyDetails(stateProperty.itemSelected)
            return
        }

        if(stateProperty.listProperties != null  && !stateProperty.isLoading){
            renderListProperties(stateProperty.listProperties, stateProperty.itemSelected)
        }

        stateProperty.errorSource?.let { renderErrorFetchingProperty(it) }

        renderIsLoading(stateProperty.isLoading)

    }

    private fun renderOpenPropertyDetails(itemPosition: Int?){
        when(activity) {
            is MainActivity -> (activity as MainActivity).openDetailsProperty()
            is SearchResultActivity -> (activity as SearchResultActivity).openDetailsProperty()
        }
    }

    private fun renderErrorFetchingProperty(errorSource: ErrorSourceListProperty){
        when(errorSource){
            ErrorSourceListProperty.NO_PROPERTY_IN_DB -> showSnackBarMessage(getString(R.string.no_properties))
            ErrorSourceListProperty.CAN_T_ACCESS_DB -> showSnackBarMessage(getString(R.string.unknown_error))
        }
    }

    protected fun currencyObserver(){
        viewModel.currency.observe(this, Observer {currency ->
            currentCurrency = currency
            renderChangeCurrency(currentCurrency!!)
        })
    }

    protected fun setPropertyPicked(property: PropertyWithAllData, itemPosition: Int?){
        viewModel.actionFromIntent(PropertyListIntent.OpenPropertyDetailIntent(property, itemPosition))

    }

    protected fun showSnackBarMessage(message: String){
        val viewLayout = activity!!.findViewById<CoordinatorLayout>(R.id.base_activity_main_layout)
        showSnackBar(viewLayout, message)

    }
}