
package com.hxtruonglhsang.cooky.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.adapter.FoodInNewsfeedAdapter;
import com.hxtruonglhsang.cooky.model.Food;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

     public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listViewNewsfeed = (ListView) view.findViewById(R.id.listviewNewsfeed);

        Food  food =new Food();
        List<String> img =new ArrayList<>(); img.add("https://static.vietnammm.com/images/restaurants/vn/NP373OQ/products/mi-quang-dac-biet.png");

        food.setName("Mỳ quảng"); food.setImgs(img); food.setUserId("lhsang"); food.setLikes(img);
        food.setDescription("Mì Quảng là một món ăn đặc trưng của Quảng Nam, Việt Nam, cùng với món cao lầu.\n" +
                "\n" +
                "Mì Quảng thường được làm từ sợi mì bằng bột gạo xay mịn và tráng thành từng lớp bánh mỏng, sau đó thái theo chiều ngang để có những sợi mì mỏng khoảng 2mm. Sợi mì làm bằng bột mỳ được trộn thêm một số phụ gia cho đạt độ giòn, dai. Dưới lớp mì là các loại rau sống, trên mì là thịt heo nạc, tôm, thịt gà cùng với nước dùng được hầm từ xương heo. Người ta còn bỏ thêm đậu phụng rang khô và giã dập, hành lá thái nhỏ, rau thơm, ớt đỏ... Thông thường nước dùng rất ít.");
        ArrayList<Food> foods =new ArrayList<>();
        foods.add(food);        foods.add(food); foods.add(food);

        FoodInNewsfeedAdapter foodAdapter = new FoodInNewsfeedAdapter(getActivity(),R.layout.newsfeed, foods);
        listViewNewsfeed.setAdapter(foodAdapter);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
