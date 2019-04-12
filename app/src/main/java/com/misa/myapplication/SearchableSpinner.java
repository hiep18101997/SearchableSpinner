package com.misa.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class SearchableSpinner extends AppCompatAutoCompleteTextView implements View.OnClickListener, View.OnFocusChangeListener, AppCompatAutoCompleteTextView.Validator, AdapterView.OnItemClickListener {
    private String[] data;
    private String value;
    private OnSelectionListener selectionListener;

    public SearchableSpinner(Context context) {
        super(context);
        init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SearchableSpinner);
        applyAttributes(a);
        init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SearchableSpinner, defStyleAttr, 0);
        applyAttributes(a);
        init();
    }

    private void init() {
        this.setBackgroundColor(Color.WHITE);
        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (12 * scale + 0.5f);
        this.setSingleLine();
        this.setPadding(padding, padding, padding, padding);
        this.setThreshold(1);
        this.setOnClickListener(this);
        this.setOnFocusChangeListener(this);
        this.setValidator(this);
        this.setOnItemClickListener(this);
        this.setText(null);
    }

    private void applyAttributes(TypedArray a) {
        try {
            setDefaultText(getText());
            setDefaultText(a.getString(R.styleable.SearchableSpinner_defaultText));
            setInvalidTextColor(a.getColor(R.styleable.SearchableSpinner_invalidTextColor, getCurrentHintTextColor()));
        } finally {
            a.recycle();
        }
    }


    public void setData(String[] data) {
        this.data = data;
        setAdapter(this.data);

    }

    public void setData(ArrayList<String> data) {
        this.data = data.toArray(new String[data.size()]);
        setAdapter(this.data);
    }


    @Override
    public void setText(CharSequence text, boolean filter) {
        setDefaultText(text);
    }

    private void setAdapter(String[] data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, data);
        setAdapter(adapter);
    }


    public void setDefaultText(CharSequence defaultText) {
        if (defaultText != null)
            setHint(defaultText);
    }

    public void setDefaultText(@StringRes int defaultText) {
        setHint(defaultText);
    }


    public void setInvalidTextColor(@ColorInt int invalidTextColor) {
        setHintTextColor(invalidTextColor);
    }


    public void setSelectionListener(OnSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }


    public String getValue() {
        performValidation();
        return value;
    }


    @Override
    public void onClick(View v) {
        this.showDropDown();

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus)
            showDropDown();
        else
            performValidation();
    }


    @Override
    public boolean isValid(CharSequence text) {
        for (String s : data) {
            if (s.equalsIgnoreCase(text.toString())) {
                value = s;
                return true;
            }
        }
        value = null;
        return false;
    }


    @Override
    public CharSequence fixText(CharSequence invalidText) {
        return null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectionListener.onSelect(getId(), position, (String) parent.getItemAtPosition(position));
    }


    public interface OnSelectionListener {

        void onSelect(int spinnerId, int position, String value);
    }
}