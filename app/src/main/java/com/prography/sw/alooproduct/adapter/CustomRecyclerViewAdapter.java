package com.prography.sw.alooproduct.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.sw.alooproduct.R;
import com.prography.sw.alooproduct.data.response.data.MyOrder;
import com.prography.sw.alooproduct.data.response.data.OrderData;
import com.prography.sw.alooproduct.model.HomeResturant;
import com.prography.sw.alooproduct.model.HomeSuperPharm;
import com.prography.sw.alooproduct.model.ImageText;
import com.prography.sw.alooproduct.model.Notification;
import com.prography.sw.alooproduct.model.OrderDetails;
import com.prography.sw.alooproduct.model.OrderItem;
import com.prography.sw.alooproduct.model.OrderListTiem;
import com.prography.sw.alooproduct.model.PreparingOrder;
import com.prography.sw.alooproduct.model.Question;
import com.prography.sw.alooproduct.model.Radio;
import com.prography.sw.alooproduct.model.ResturantStore;
import com.prography.sw.alooproduct.model.Section;
import com.prography.sw.alooproduct.model.StoreProductItem;
import com.prography.sw.alooproduct.model.Transaction;
import com.prography.sw.alooproduct.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CustomListener customListener;
    private HolderConstants holderConstants;

    private List<String> titles = new ArrayList<>();
    private List<Radio> radios = new ArrayList<>();
    private List<ImageText> imageTexts = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private List<Section> sections = new ArrayList<>();
    private List<OrderItem> homeResturant = new ArrayList<>();
    private List<OrderListTiem> orderDetails = new ArrayList<>();
    private List<OrderListTiem> preparingOrders = new ArrayList<>();
    private List<StoreProductItem> homeSuperPharm = new ArrayList<>();
    private List<StoreProductItem> resturantStores = new ArrayList<>();
    private List<MyOrder> myOrders = new ArrayList<>();
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
            case ADD_SECTION:
                return new SectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false), customListener, holderConstants);
            case HOME_FRAGMET_RESTURANT:
                if (viewType == Constants.LOADING_CASE) {
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                } else {
                    return new HomeFragmetResturantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_resturant, parent, false), customListener, holderConstants);
                }
            case ITEM_ORDER_DETAILS:
                return new OrderDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false), customListener, holderConstants);
            case ITEM_PREPARING_ORDER:
                return new PreparingOrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preparing_order, parent, false), customListener, holderConstants);
            case HOME_FRAGMENT_SUPER_PHARM:
                return new HomeFragmetSuperPharmViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_super_pharm, parent, false), customListener, holderConstants);
            case RESTAURANTS_STORE_ITEM:
                if (viewType == Constants.LOADING_CASE) {
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                } else {
                    return new RestaurantStoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stor_resturant, parent, false), customListener, holderConstants);
                }
            case MY_ORDER:
                if (viewType == Constants.LOADING_CASE) {
                    return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                } else {
                    return new MyOrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_order, parent, false), customListener, holderConstants);
                }
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
            case ADD_SECTION:
                ((SectionViewHolder) holder).onBind(sections.get(position));
                break;
            case HOME_FRAGMET_RESTURANT:
                if (homeResturant.get(position).getId() != Constants.LOADING_CASE)
                    ((HomeFragmetResturantViewHolder) holder).onBind(homeResturant.get(position));
                break;
            case ITEM_ORDER_DETAILS:
                ((OrderDetailsViewHolder) holder).onBind(orderDetails.get(position));
                break;
            case ITEM_PREPARING_ORDER:
                ((PreparingOrderViewHolder) holder).onBind(preparingOrders.get(position));
                break;
            case HOME_FRAGMENT_SUPER_PHARM:
                ((HomeFragmetSuperPharmViewHolder) holder).onBind(homeSuperPharm.get(position));
                break;
            case RESTAURANTS_STORE_ITEM:
                ((RestaurantStoreViewHolder) holder).onBind(resturantStores.get(position));
                break;
            case MY_ORDER:
                if (myOrders.get(position).getId() != Constants.LOADING_CASE)
                    ((MyOrderViewHolder) holder).onBind(myOrders.get(position));
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
            case NOTIFICATION:
                return notifications.size();
            case QUESTION:
                return questions.size();
            case ADD_SECTION:
                return sections.size();
            case HOME_FRAGMET_RESTURANT:
                return homeResturant.size();
            case ITEM_ORDER_DETAILS:
                return orderDetails.size();
            case ITEM_PREPARING_ORDER:
                return preparingOrders.size();
            case HOME_FRAGMENT_SUPER_PHARM:
                return homeSuperPharm.size();
            case RESTAURANTS_STORE_ITEM:
                return resturantStores.size();
            case MY_ORDER:
                return myOrders.size();
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
        NOTIFICATION,
        QUESTION,
        ADD_SECTION,
        HOME_FRAGMET_RESTURANT,
        ITEM_ORDER_DETAILS,
        ITEM_PREPARING_ORDER,
        HOME_FRAGMENT_SUPER_PHARM,
        RESTAURANTS_STORE_ITEM,
        MY_ORDER,
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
                case HOME_FRAGMET_RESTURANT:
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(Constants.LOADING_CASE);
                    homeResturant.add(orderItem);
                    notifyDataSetChanged();
                    break;
                case MY_ORDER:
                    MyOrder myOrder = new MyOrder();
                    myOrder.setId(Constants.LOADING_CASE);
                    myOrders.add(myOrder);
                    notifyDataSetChanged();
                    break;
                case RESTAURANTS_STORE_ITEM:
                    StoreProductItem storeProductItem = new StoreProductItem();
                    storeProductItem.setId(Constants.LOADING_CASE);
                    resturantStores.add(storeProductItem);
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
                case HOME_FRAGMET_RESTURANT:
                    if (homeResturant != null) {
                        if (homeResturant.size() > 0) {
                            if (homeResturant.get(homeResturant.size() - 1).getId() == Constants.LOADING_CASE) {
                                homeResturant.remove(homeResturant.size() - 1);
                            }
                        }
                    }
                    break;
                case MY_ORDER:
                    if (myOrders != null) {
                        if (myOrders.size() > 0) {
                            if (myOrders.get(myOrders.size() - 1).getId() == Constants.LOADING_CASE) {
                                myOrders.remove(myOrders.size() - 1);
                            }
                        }
                    }
                    break;
                case RESTAURANTS_STORE_ITEM:
                    if (resturantStores != null) {
                        if (resturantStores.size() > 0) {
                            if (resturantStores.get(resturantStores.size() - 1).getId() == Constants.LOADING_CASE) {
                                resturantStores.remove(resturantStores.size() - 1);
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
            case HOME_FRAGMET_RESTURANT:
                if (homeResturant != null) {
                    if (homeResturant.size() > 0) {
                        if (homeResturant.get(homeResturant.size() - 1).getId() == Constants.LOADING_CASE) {
                            return true;
                        }
                    }
                }
                break;
            case MY_ORDER:
                if (myOrders != null) {
                    if (myOrders.size() > 0) {
                        if (myOrders.get(myOrders.size() - 1).getId() == Constants.LOADING_CASE) {
                            return true;
                        }
                    }
                }
                break;
            case RESTAURANTS_STORE_ITEM:
                if (resturantStores != null) {
                    if (resturantStores.size() > 0) {
                        if (resturantStores.get(resturantStores.size() - 1).getId() == Constants.LOADING_CASE) {
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
            case HOME_FRAGMET_RESTURANT:
                if (homeResturant.get(position).getId() == Constants.LOADING_CASE) {
                    return Constants.LOADING_CASE;
                }
                break;
            case MY_ORDER:
                if (myOrders.get(position).getId() == Constants.LOADING_CASE) {
                    return Constants.LOADING_CASE;
                }
                break;
            case RESTAURANTS_STORE_ITEM:
                if (resturantStores.get(position).getId() == Constants.LOADING_CASE) {
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


    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }


    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setHomeResturant(List<OrderItem> homeResturant) {
        this.homeResturant = homeResturant;
    }

    public void setOrderDetails(List<OrderListTiem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setPreparingOrder(List<OrderListTiem> preparingOrders) {
        this.preparingOrders = preparingOrders;
    }

    public void setHomeSuperPharm(List<StoreProductItem> homeSuperPharm) {
        this.homeSuperPharm = homeSuperPharm;
    }

    public void setResturantStore(List<StoreProductItem> resturantStores) {
        this.resturantStores = resturantStores;
    }

    public void setMyOrders(List<MyOrder> myOrders) {
        this.myOrders = myOrders;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}

