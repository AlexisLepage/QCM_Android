package com.iia.cdsm.myqcm.View.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.User;
import com.iia.cdsm.myqcm.R;

/**
 * Created by Alex on 16/06/2016.
 */
public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView tvName = (TextView) view.findViewById(R.id.tvNameUser);
        TextView tvFirstname = (TextView) view.findViewById(R.id.tvFirstnameUser);
        TextView tvLogin = (TextView) view.findViewById(R.id.tvLoginUser);
        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmailUser);

        User user = (User) getActivity().getIntent().getExtras().get("user");

        assert user != null;
        tvName.setText(user.getName());
        tvFirstname.setText(user.getFirstname());
        tvLogin.setText(user.getLogin());
        tvEmail.setText(user.getEmail());

        return view;
    }
}
