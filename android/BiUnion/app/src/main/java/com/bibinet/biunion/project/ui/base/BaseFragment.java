package com.bibinet.biunion.project.ui.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.utils.LoactionUtils;

import java.io.Serializable;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by bibinet on 2017-6-20.
 */

public abstract class BaseFragment extends SupportFragment {

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutView();
        if (view == null) {
            return inflater.inflate(getLayoutId(), container, false);
        }
        return view;
    }

    protected View getLayoutView() {
        return null;
    }

    protected abstract int getLayoutId();

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onBaseViewCreated(view, savedInstanceState);
    }

    protected final View createView(int layoutId) {
        return LayoutInflater.from(getActivity()).inflate(layoutId, null, false);
    }

    protected final View findView(View parent, int viewId) {
        return parent.findViewById(viewId);
    }

    protected abstract void onBaseViewCreated(View view, Bundle savedInstanceState);

    protected void setSerializable(String key, Serializable serializable) {
        Bundle b = new Bundle();
        b.putSerializable(key, serializable);
        setArguments(b);
    }

    protected Serializable getSerializable(String key) {
        return getArguments().getSerializable(key);
    }
}
