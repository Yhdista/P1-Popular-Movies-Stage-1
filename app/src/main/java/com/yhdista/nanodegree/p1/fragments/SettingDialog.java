package com.yhdista.nanodegree.p1.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.yhdista.nanodegree.p1.abstracts.MyBasicDialogFragment;
import com.yhdista.nanodegree.p1.constants.C;
import com.yhdista.nanodegree.p1.interfaces.DatasetCallbacks;
import com.yhdista.nanodegree.p1.oodesign.SortItems;

/**
 * Setting Dialog for sorting option
 */
public class SettingDialog extends MyBasicDialogFragment {



    public static SettingDialog newRetainedInstance() {


        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);
        setMyCancelable(bundleArgs, true);
        setTitle(bundleArgs, "Sort movies by:");
        setMessage(bundleArgs, "generic");

        SettingDialog fragment = new SettingDialog();
        fragment.setArguments(bundleArgs);

        return fragment;

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);

        dialog.setTitle(getArguments().getString(TAG_DIALOG_TITLE));
        // dialog.setMessage(getArguments().getString(TAG_DIALOG_MESSAGE)); // otherwise no items will be shown!!!

        dialog.setItems(SortItems.getSortItemsNames(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((DatasetCallbacks) mFragmentManager.findFragmentByTag(C.TAG_FRAGMENT_MAIN)).sortBy(which);
            }
        });

        return dialog.create();

    }

}
