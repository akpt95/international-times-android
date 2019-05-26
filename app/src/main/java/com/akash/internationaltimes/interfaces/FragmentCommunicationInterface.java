package com.akash.internationaltimes.interfaces;

import android.os.Bundle;

import androidx.annotation.Nullable;

public interface FragmentCommunicationInterface {

    void replaceFragment(String fragmentName, String tag, @Nullable Bundle args, boolean requiresBackStackAddition);

    void setUpHomeWithNewsData();
}
