<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRestaurantDataTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('restaurant_data', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('vendor_id');
            $table->unsignedBigInteger('restaurant_type_id')->nullable();
            $table->unsignedBigInteger('kitchen_type_id')->nullable();
            $table->timestamps();

            $table->foreign('vendor_id')->references('id')->on((new \App\Models\Vendor())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('restaurant_type_id')->references('id')->on((new \App\Models\RestaurantTypes())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('kitchen_type_id')->references('id')->on((new \App\Models\KitchenType())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('restaurant_data');
    }
}
