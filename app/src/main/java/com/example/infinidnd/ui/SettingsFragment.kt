package com.example.android.githubsearchwithsettings.ui

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.infinidnd.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

//        val modePref: ListPreference? = findPreference(
//            getString(R.string.pref_mode_key)
//        )

//        if (modePref?.value == "light") {
//            setTheme(R.style.Theme_InfiniDnDlight)
//        } else {
//            useTheme(R.style.Theme_InfiniDnDdark)
//        }
//
//        val languagePref: MultiSelectListPreference? = findPreference(
//            getString(R.string.pref_language_key)
//        )
//
//        languagePref?.summaryProvider = Preference.SummaryProvider<MultiSelectListPreference> {
//            val n = it.values.size
//            if (n == 0) {
//                getString(R.string.pref_language_not_set)
//            } else {
//                resources.getQuantityString(R.plurals.pref_language_summary, n, n)
//            }
//        }
    }
}