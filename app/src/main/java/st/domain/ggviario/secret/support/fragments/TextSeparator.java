package st.domain.ggviario.secret.support.fragments;

import android.view.View;
import android.widget.TextView;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.support.ItemDataSet;
import st.domain.ggviario.secret.support.ItemViewHolder;
import st.domain.ggviario.secret.support.RecyclerViewAdapter;

/**
 *
 * Created by xdata on 12/25/16.
 */

public class TextSeparator {

    public static int LAYOUT_ID = R.layout.item_textseparator;

    public static RecyclerViewAdapter.ViewHolderFactory factoryInstance() {
        return new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new TextSeparatorViewHolder(view);
            }
        };
    }

    public  static class TextSeparatorDataSet implements ItemDataSet {

        private String text;

        public TextSeparatorDataSet(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_textseparator;
        }
    }


    static class TextSeparatorViewHolder extends ItemViewHolder{

        private TextView tvText;
        private TextSeparatorDataSet dataSet;

        TextSeparatorViewHolder(View itemView) {
            super(itemView);
            this.tvText = (TextView) itemView.findViewById(R.id.tv_text_separator);
        }

        public void bind(ItemDataSet dataSet){
            this.dataSet = (TextSeparatorDataSet) dataSet;
            this.tvText.setText(this.dataSet.getText());
        }
    }
}
