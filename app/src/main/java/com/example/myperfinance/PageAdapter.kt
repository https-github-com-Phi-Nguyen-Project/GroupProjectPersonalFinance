package com.example.myperfinance

import android.accounts.Account
import android.content.Context
import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


private val TAB_TITLES = arrayOf(
    "Transactions",
    "Account",
    "Investment",
    "Setting"
)

class PageAdapter (private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val registeredFragments = SparseArray<Fragment>()

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> {
                ItemsFragment()
            }
            1 -> {
                AccountFragment()
            }
            2 -> {
                InvestmentFragment()
            }
            3 -> {
                SettingFragment()
            }
            else -> {
                return ItemsFragment()
            }
        }
        //return PlaceholderFragment.newInstance(position + 1)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

    fun getRegisteredFragments(): SparseArray<Fragment> {
        return registeredFragments
    }


    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }


}