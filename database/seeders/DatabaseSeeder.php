<?php

namespace Database\Seeders;

use App\Models\About;
use App\Models\Admin;
use App\Models\Bank;
use App\Models\Branch;
use App\Models\BranchItems;
use App\Models\BranchOffer;
use App\Models\Car;
use App\Models\CartItem;
use App\Models\Category;
use App\Models\City;
use App\Models\Config;
use App\Models\Customer;
use App\Models\CustomerCart;
use App\Models\CustomerPlaces;
use App\Models\Drink;
use App\Models\Driver;
use App\Models\DriverCityPrice;
use App\Models\DriverWallet;
use App\Models\DriverWalletTrack;
use App\Models\Extra;
use App\Models\faq;
use App\Models\Item;
use App\Models\KitchenType;
use App\Models\LanguageField;
use App\Models\Offer;
use App\Models\Order;
use App\Models\OrderCancel;
use App\Models\OrderItem;
use App\Models\Package;
use App\Models\Privacy;
use App\Models\RestaurantData;
use App\Models\RestaurantTypes;
use App\Models\Review;
use App\Models\SupportedVendor;
use App\Models\UserFavorite;
use App\Models\UserWallet;
use App\Models\UserWalletTrack;
use App\Models\Vendor;
use App\Models\VendorWallet;
use App\Models\VendorWalletTrack;
use Database\Factories\AboutFactory;
use Database\Factories\BranchItemsFactory;
use Database\Factories\CustomerCartFactory;
use Database\Factories\ItemFactory;
use Database\Factories\LanguageFieldFactory;
use Database\Factories\PrivacyFactory;
use Database\Factories\RestaurantDataFactory;
use Database\Factories\SupportedVendorFactory;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        Config::factory(4)->create();
        Admin::factory(1)->create();
        City::factory(3)->create();
        faq::factory(30)->create();
        About::factory(AboutFactory::getCount())->create();
        Privacy::factory(PrivacyFactory::getCount())->create();
        SupportedVendor::factory(SupportedVendorFactory::getCount())->create();
        RestaurantTypes::factory(1)->create();
        KitchenType::factory(1)->create();
        Package::factory(50)->create();
        Customer::factory(4)->create();
        DriverCityPrice::factory(City::count())->create();

        if (env('APP_ENV')==='local'||env('APP_ENV')==='testing')
        {
//            Vendor::factory(150)->create();
//            Branch::factory(500)->create();
//            RestaurantData::factory(RestaurantDataFactory::getCount())->create();
//            Drink::factory(300)->create();
//            Extra::factory(300)->create();
//            Category::factory(350)->create();
//            Category::factory(150)->create();

//            Item::factory(150)->create();
//            ItemFactory::$isRand = false;
//            Item::factory(150)->create();

//            Driver::factory(300)->create();
//            Car::factory(250)->create();
//            UserFavorite::factory(50)->create();
//            UserWallet::factory(Customer::count())->create();
//            UserWalletTrack::factory(Customer::count() * 20)->create();
//            CustomerPlaces::factory(150)->create();

//            Order::factory(300)->create();
//            if (env('APP_ENV')!=='local')
//            {
//                Order::all()->each(function ($order){
//                    $order->makeRealtime();
//                });
//            }
//            OrderItem::factory(1000)->create();
//            OrderCancel::factory(100)->create();

//            Offer::factory(200)->create();

//            CustomerCart::factory(CustomerCartFactory::getCount())->create();
//            CartItem::factory(500)->create();

//            BranchItems::factory(BranchItemsFactory::getCount())->create();

//            DriverWallet::factory(Car::count())->create();
//            DriverWalletTrack::factory(Car::count() * 20)->create();

//            VendorWallet::factory(Branch::count())->create();
//            VendorWalletTrack::factory(Branch::count() * 20)->create();

//            DriverCityPrice::factory(City::count())->create();
//            BranchOffer::factory(400)->create();
        }
    }
}
