package com.wks.nearby.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waqqas on 3/22/2016.
 */
public class SimpleSpanBuilder {
    private final List<SpanSection> spanSections;
    private final StringBuilder stringBuilder;
    public SimpleSpanBuilder(){
        stringBuilder = new StringBuilder();
        spanSections = new ArrayList<>();
    }

    public SimpleSpanBuilder append(String text,Object... spans){
        if (spans != null && spans.length > 0) {
            spanSections.add(new SpanSection(text, stringBuilder.length(),spans));
        }
        stringBuilder.append(text);
        return this;
    }

    public SimpleSpanBuilder appendWithSpace(String text, Object... spans){
        return append(text.concat(" "), spans);
    }

    public SimpleSpanBuilder appendWithLinebreak(String text,Object... spans){
        return append(text.concat("\n"),spans);
    }

    public Spanned toSpanned(){
        SpannableStringBuilder ssb = new SpannableStringBuilder(stringBuilder.toString());
        for (SpanSection section : spanSections){
            section.apply(ssb);
        }
        return ssb;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    private final class SpanSection{
        private final String text;
        private final int startIndex;
        private final Object[] spans;

        private SpanSection(String text, int startIndex, Object... spans) {
            this.spans = spans;
            this.text = text;
            this.startIndex = startIndex;
        }

        private void apply(SpannableStringBuilder spanStringBuilder) {
            if (spanStringBuilder == null) return;
            for (Object span : spans){
                spanStringBuilder.setSpan(span, startIndex, startIndex + text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }

}