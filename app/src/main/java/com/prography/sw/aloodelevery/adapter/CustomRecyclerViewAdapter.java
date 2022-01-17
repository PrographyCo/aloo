package com.prography.sw.aloodelevery.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloodelevery.R;
import com.prography.sw.aloodelevery.model.Cart;
import com.prography.sw.aloodelevery.model.HomeFragment;
import com.prography.sw.aloodelevery.model.ImageText;
import com.prography.sw.aloodelevery.model.MyOrder;
import com.prography.sw.aloodelevery.model.MyRides;
import com.prography.sw.aloodelevery.model.Order;
import com.prography.sw.aloodelevery.model.OrderDetailsHome;
import com.prography.sw.aloodelevery.model.OrderItem;
import com.prography.sw.aloodelevery.model.Question;
import com.prography.sw.aloodelevery.model.Radio;
import com.prography.sw.aloodelevery.model.Notification;
import com.prography.sw.aloodelevery.model.Transaction;
import com.prography.sw.aloodelevery.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CustomListener customListener;
    private HolderConstants holderConstants;

    private List<String> titles = new ArrayList<>();
    private List<Radio> radios = new ArrayList<>();
    private List<ImageText> imageTexts = new ArrayList<>();
    private List<Cart> carts = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private List<Order> homeFragments = new ArrayList<>();
    private List<OrderItem> orderDetailsHomes = new ArrayList<>();
    private List<MyOrder> myRides = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();


    public CustomRecyclerViewAdapter(CustomListener customListener, HolderConstants holderConstants) {
        this.customListener = customListener;
        this.holderConstants = holderConstants;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (holderConstants) {
            case BOTTOM_SHEET_ITEM_TEXT:
                return new BottomSheetItemTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_text, parent, false), customListener, holderConstants);
            case BOTTOM_SHEET_ITEM_RADIO_1:
            case BOTTOM_SHEET_ITEM_RADIO_2:
                return new BottomSheetItemRadioViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_radio, parent, false), customListener, holderConstants);
            case BOTTOM_SHEET_ITEM_IMG_TEXT:
                return new BottomSheetItemImgTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_img_text, parent, false), customListener, holderConstants);
            case NOTIFICATION:
                if (viewType == Constants.LOADING_CASE)
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                else
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false), customListener, holderConstants);
            case QUESTION:
                if (viewType == Constants.LOADING_CASE) {
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                } else {
                    return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false), customListener, holderConstants);
                }
            case HOME_FRAGMENT:
                if (viewType == Constants.LOADING_CASE) {
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                } else {
                    return new HomeFragmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fragment, parent, false), customListener, holderConstants);

                }
            case ORDER_DETAILS_HOME:
                return new OrderDetailsHomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false), customListener, holderConstants);
            case MY_RIDES:
                return new MyRidesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_rides_fragment, parent, false), customListener, holderConstants);
            case TRANSACTION:
                return new TransactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false), customListener, holderConstants);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holderConstants) {
            case BOTTOM_SHEET_ITEM_TEXT:
                ((BottomSheetItemTextViewHolder) holder).onBind(titles.get(position));
                break;
            case BOTTOM_SHEET_ITEM_RADIO_1:
            case BOTTOM_SHEET_ITEM_RADIO_2:
                ((BottomSheetItemRadioViewHolder) holder).onBind(radios.get(position));
                break;
            case BOTTOM_SHEET_ITEM_IMG_TEXT:
                ((BottomSheetItemImgTextViewHolder) holder).onBind(imageTexts.get(position));
                break;
            case NOTIFICATION:
                if (notifications.get(position).getId() != Constants.LOADING_CASE)
                ((NotificationViewHolder) holder).onBind(notifications.get(position));
                break;
            case QUESTION:
                if (questions.get(position).getId() != Constants.LOADING_CASE)
                    ((QuestionViewHolder) holder).onBind(questions.get(position));
                break;
            case HOME_FRAGMENT:
                if (homeFragments.get(position).getId() != Constants.LOADING_CASE)
                    ((HomeFragmentViewHolder) holder).onBind(homeFragments.get(position));
                break;
            case ORDER_DETAILS_HOME:
                ((OrderDetailsHomeViewHolder) holder).onBind(orderDetailsHomes.get(position));
                break;
            case MY_RIDES:
                ((MyRidesViewHolder) holder).onBind(myRides.get(position));
                break;
            case TRANSACTION:
                ((TransactionViewHolder) holder).onBind(transactions.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (holderConstants) {
            case BOTTOM_SHEET_ITEM_TEXT:
                return titles.size();
            case BOTTOM_SHEET_ITEM_RADIO_1:
            case BOTTOM_SHEET_ITEM_RADIO_2:
                return radios.size();
            case BOTTOM_SHEET_ITEM_IMG_TEXT:
                return imageTexts.size();
            case CART_ITEM:
                return carts.size();
            case NOTIFICATION:
                return notifications.size();
            case QUESTION:
                return questions.size();
            case HOME_FRAGMENT:
                return homeFragments.size();
            case ORDER_DETAILS_HOME:
                return orderDetailsHomes.size();
            case MY_RIDES:
                return myRides.size();
            case TRANSACTION:
                return transactions.size();

            default:
                return 0;
        }
    }

    public HolderConstants getHolderConstants() {
        return holderConstants;
    }

    public void setHolderConstants(HolderConstants holderConstants) {
        this.holderConstants = holderConstants;
    }

    public void displayLoading() {
        // loading at bottom of screen
        if (!isLoading()) {
            switch (holderConstants) {
                case QUESTION:
                    Question question = new Question();
                    question.setId(Constants.LOADING_CASE);
                    questions.add(question);
                    notifyDataSetChanged();
                    break;
                case HOME_FRAGMENT:
                    Order order = new Order();
                    order.setId(Constants.LOADING_CASE);
                    homeFragments.add(order);
                    notifyDataSetChanged();
                    break;
                case NOTIFICATION:
                    Notification notification = new Notification();
                    notification.setId(Constants.LOADING_CASE);
                    notifications.add(notification);
                    notifyDataSetChanged();
                    break;

            }
        }
    }

    public void hideLoading() {
        if (isLoading()) {
            switch (holderConstants) {
                case QUESTION:
                    if (questions != null) {
                        if (questions.size() > 0) {
                            if (questions.get(questions.size() - 1).getId() == Constants.LOADING_CASE) {
                                questions.remove(questions.size() - 1);
                            }
                        }
                    }
                    break;
                case HOME_FRAGMENT:
                    if (homeFragments != null) {
                        if (homeFragments.size() > 0) {
                            if (homeFragments.get(homeFragments.size() - 1).getId() == Constants.LOADING_CASE) {
                                homeFragments.remove(homeFragments.size() - 1);
                            }
                        }
                    }
                    break;
                case NOTIFICATION:
                    if (notifications != null) {
                        if (notifications.size() > 0) {
                            if (notifications.get(notifications.size() - 1).getId() == Constants.LOADING_CASE) {
                                notifications.remove(notifications.size() - 1);
                            }
                        }
                    }
                    break;
            }
        }
        notifyDataSetChanged();
    }

    private boolean isLoading() {
        switch (holderConstants) {
            case QUESTION:
                if (questions != null) {
                    if (questions.size() > 0) {
                        if (questions.get(questions.size() - 1).getId() == Constants.LOADING_CASE) {
                            return true;
                        }
                    }
                }
                break;
            case HOME_FRAGMENT:
                if (homeFragments != null) {
                    if (homeFragments.size() > 0) {
                        if (homeFragments.get(homeFragments.size() - 1).getId() == Constants.LOADING_CASE) {
                            return true;
                        }
                    }
                }
                break;
            case NOTIFICATION:
                if (notifications != null) {
                    if (notifications.size() > 0) {
                        if (notifications.get(notifications.size() - 1).getId() == Constants.LOADING_CASE) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        switch (holderConstants) {
            case QUESTION:
                if (questions.get(position).getId() == Constants.LOADING_CASE) {
                    return Constants.LOADING_CASE;
                }
                break;
            case HOME_FRAGMENT:
                if (homeFragments.get(position).getId() == Constants.LOADING_CASE) {
                    return Constants.LOADING_CASE;
                }
                break;
            case NOTIFICATION:
                if (notifications.get(position).getId() == Constants.LOADING_CASE) {
                    return Constants.LOADING_CASE;
                }
                break;
        }
        return -1;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public void setRadios(List<Radio> radios) {
        this.radios = radios;
    }

    public void setItemImageText(List<ImageText> imageTexts) {
        this.imageTexts = imageTexts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setHomeFragment(List<Order> homeFragments) {
        this.homeFragments = homeFragments;
    }

    public void setOrderDetailsHome(List<OrderItem> orderDetailsHomes) {
        this.orderDetailsHomes = orderDetailsHomes;
    }

    public void setMyRides(List<MyOrder> myRides) {
        this.myRides = myRides;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public enum HolderConstants {
        BOTTOM_SHEET_ITEM_TEXT,
        BOTTOM_SHEET_ITEM_RADIO_1,
        BOTTOM_SHEET_ITEM_RADIO_2,
        BOTTOM_SHEET_ITEM_IMG_TEXT,
        CART_ITEM,
        NOTIFICATION,
        QUESTION,
        HOME_FRAGMENT,
        ORDER_DETAILS_HOME,
        MY_RIDES,
        TRANSACTION,

    }

}

