package com.prography.sw.aloocustomer.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.CartItem;
import com.prography.sw.aloocustomer.model.IAddress;
import com.prography.sw.aloocustomer.model.Cart;
import com.prography.sw.aloocustomer.model.MainListItem;
import com.prography.sw.aloocustomer.model.ImageText;
import com.prography.sw.aloocustomer.model.MyOrder;
import com.prography.sw.aloocustomer.model.OrderItem;
import com.prography.sw.aloocustomer.model.Question;
import com.prography.sw.aloocustomer.model.Radio;
import com.prography.sw.aloocustomer.model.MealDetails;
import com.prography.sw.aloocustomer.model.Notification;
import com.prography.sw.aloocustomer.util.Constants;
import com.prography.sw.aloocustomer.model.VendorItems;
import com.prography.sw.aloocustomer.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int senderId;
    private CustomListener customListener;
    private HolderConstants holderConstants;

    private List<String> titles = new ArrayList<>();
    private List<Radio> radios = new ArrayList<>();
    private List<ImageText> imageTexts = new ArrayList<>();
    private List<MainListItem> restaurants = new ArrayList<>();
    private List<VendorItems> vendorItems = new ArrayList<>();
    private List<MealDetails> mealDetails = new ArrayList<>();
    private List<CartItem> carts = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private List<MainListItem> supermarkets = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private List<MyOrder> MyOrders = new ArrayList<>();
    private List<OrderItem> orderItems = new ArrayList<>();
    private List<IAddress> IAddresses = new ArrayList<>();
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
            case RESTAURANTS_ITEM:
                if (viewType == Constants.LOADING_CASE)
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                else
                    return new RestaurantsItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resturants_recycler, parent, false), customListener, holderConstants);
            case RESTAURANTS_PAGE_ITEM:
                if (viewType == Constants.LOADING_CASE)
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                else
                    return new RestaurantPageItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resturant_page_recycler, parent, false), customListener, holderConstants);
            case MEAL_DETAILS:
                return new MealDitelsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_box, parent, false), customListener, holderConstants);
            case CART_ITEM:
                return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false), customListener, holderConstants);
            case NOTIFICATION:
                if (viewType == Constants.LOADING_CASE)
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                else
                    return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false), customListener, holderConstants);
            case ITEM_SUPERMARKET:
                return new SupermarketViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supermarket, parent, false), customListener, holderConstants);
            case ITEM_SUPERMARKET_PAGE:
                if (viewType == Constants.LOADING_CASE)
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                else
                    return new SupermarketPageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supermarket_page, parent, false), customListener, holderConstants);
            case ITEM_ORDERS_LIST:
                return new OrdersLsitViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_list, parent, false), customListener, holderConstants);
            case QUESTION:
                if (viewType == Constants.LOADING_CASE)
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                else
                    return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false), customListener, holderConstants);
            case ITEM_ORDERS_DETAILS:
                return new OrderDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false), customListener, holderConstants);
            case LOCATION:
                return new LocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false), customListener, holderConstants);
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
            case RESTAURANTS_ITEM:
                if (restaurants.get(position).getId() != Constants.LOADING_CASE)
                    ((RestaurantsItemViewHolder) holder).onBind(restaurants.get(position));
                break;
            case RESTAURANTS_PAGE_ITEM:
                if (vendorItems.get(position).getId() != Constants.LOADING_CASE)
                    ((RestaurantPageItemViewHolder) holder).onBind(vendorItems.get(position));
                break;
            case MEAL_DETAILS:
                ((MealDitelsViewHolder) holder).onBind(mealDetails.get(position));
                break;
            case CART_ITEM:
                ((CartViewHolder) holder).onBind(carts.get(position));
                break;
            case NOTIFICATION:
                if (notifications.get(position).getId() != Constants.LOADING_CASE)
                    ((NotificationViewHolder) holder).onBind(notifications.get(position));
                break;
            case ITEM_SUPERMARKET:
                ((SupermarketViewHolder) holder).onBind(supermarkets.get(position));
                break;
            case ITEM_SUPERMARKET_PAGE:
                if (vendorItems.get(position).getId() != Constants.LOADING_CASE)
                    ((SupermarketPageViewHolder) holder).onBind(vendorItems.get(position));
                break;
            case QUESTION:
                if (questions.get(position).getId() != Constants.LOADING_CASE)
                    ((QuestionViewHolder) holder).onBind(questions.get(position));
                break;
            case ITEM_ORDERS_LIST:
                ((OrdersLsitViewHolder) holder).onBind(MyOrders.get(position));
                break;
            case ITEM_ORDERS_DETAILS:
                ((OrderDetailsViewHolder) holder).onBind(orderItems.get(position));
                break;
            case LOCATION:
                ((LocationViewHolder) holder).onBind(IAddresses.get(position));
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
            case RESTAURANTS_ITEM:
                return restaurants.size();
            case RESTAURANTS_PAGE_ITEM:
            case ITEM_SUPERMARKET_PAGE:
                return vendorItems.size();
            case MEAL_DETAILS:
                return mealDetails.size();
            case CART_ITEM:
                return carts.size();
            case NOTIFICATION:
                return notifications.size();
            case ITEM_SUPERMARKET:
                return supermarkets.size();
            case QUESTION:
                return questions.size();
            case ITEM_ORDERS_LIST:
                return MyOrders.size();
            case ITEM_ORDERS_DETAILS:
                return orderItems.size();
            case LOCATION:
                return IAddresses.size();
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

    public enum HolderConstants {
        BOTTOM_SHEET_ITEM_TEXT,
        BOTTOM_SHEET_ITEM_RADIO_1,
        BOTTOM_SHEET_ITEM_RADIO_2,
        BOTTOM_SHEET_ITEM_IMG_TEXT,
        RESTAURANTS_ITEM,
        RESTAURANTS_PAGE_ITEM,
        MEAL_DETAILS,
        CART_ITEM,
        NOTIFICATION,
        ITEM_SUPERMARKET,
        ITEM_SUPERMARKET_PAGE,
        QUESTION,
        ITEM_ORDERS_LIST,
        ITEM_ORDERS_DETAILS,
        LOCATION,
        TRANSACTION,
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
                case ITEM_SUPERMARKET_PAGE:
                case RESTAURANTS_PAGE_ITEM:
                    VendorItems vendorItem = new VendorItems();
                    vendorItem.setId(Constants.LOADING_CASE);
                    vendorItems.add(vendorItem);
                    notifyDataSetChanged();
                    break;
                case RESTAURANTS_ITEM:
                    MainListItem mainListItem = new MainListItem();
                    mainListItem.setId(Constants.LOADING_CASE);
                    restaurants.add(mainListItem);
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
                case ITEM_SUPERMARKET_PAGE:
                case RESTAURANTS_PAGE_ITEM:
                    if (vendorItems != null) {
                        if (vendorItems.size() > 0) {
                            if (vendorItems.get(vendorItems.size() - 1).getId() == Constants.LOADING_CASE) {
                                vendorItems.remove(vendorItems.size() - 1);
                            }
                        }
                    }
                    break;
                case RESTAURANTS_ITEM:
                    if (restaurants != null) {
                        if (restaurants.size() > 0) {
                            if (restaurants.get(restaurants.size() - 1).getId() == Constants.LOADING_CASE) {
                                restaurants.remove(restaurants.size() - 1);
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
            case ITEM_SUPERMARKET_PAGE:
            case RESTAURANTS_PAGE_ITEM:
                if (vendorItems != null) {
                    if (vendorItems.size() > 0) {
                        if (vendorItems.get(vendorItems.size() - 1).getId() == Constants.LOADING_CASE) {
                            return true;
                        }
                    }
                }
                break;
            case RESTAURANTS_ITEM:
                if (restaurants != null) {
                    if (restaurants.size() > 0) {
                        if (restaurants.get(restaurants.size() - 1).getId() == Constants.LOADING_CASE) {
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
            case ITEM_SUPERMARKET_PAGE:
            case RESTAURANTS_PAGE_ITEM:
                if (vendorItems.get(position).getId() == Constants.LOADING_CASE) {
                    return Constants.LOADING_CASE;
                }
                break;
            case RESTAURANTS_ITEM:
                if (restaurants.get(position).getId() == Constants.LOADING_CASE) {
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

    public void setRestaurants(List<MainListItem> restaurants) {
        this.restaurants = restaurants;
    }

    public void setVendorItems(List<VendorItems> restaurantPages) {
        this.vendorItems = restaurantPages;
    }

    public void setMealDetails(List<MealDetails> mealDetails) {
        this.mealDetails = mealDetails;
    }

    public void setCarts(List<CartItem> carts) {
        this.carts = carts;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setSupermarkets(List<MainListItem> supermarkets) {
        this.supermarkets = supermarkets;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setMyOrders(List<MyOrder> MyOrders) {
        this.MyOrders = MyOrders;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setLocations(List<IAddress> IAddresses) {
        this.IAddresses = IAddresses;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

