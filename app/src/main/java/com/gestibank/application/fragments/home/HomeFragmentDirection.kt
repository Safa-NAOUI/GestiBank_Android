package com.gestibank.application.fragments.home

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.gestibank.application.R

class HomeFragmentDirection {
        companion object {
        fun actionHomeFragmentToSignUpFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_homeFragment_to_signUpFragment)

        fun actionHomeFragmentToSignInFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_homeFragment_to_signInFragment)

        fun actionHomeFragmentToCurrencyAmountFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_homeFragment_to_currency_amountFragment)
    }

}