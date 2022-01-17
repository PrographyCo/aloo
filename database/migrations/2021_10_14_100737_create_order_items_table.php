<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateOrderItemsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('order_items', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('order_id');
            $table->unsignedBigInteger('item_id')->nullable();
            $table->unsignedBigInteger('offer_id')->nullable();
            $table->integer('amount');
            $table->float('item_price',20,2,true);
            $table->json('data')->nullable(); //{"size": "", "with": [], "without": [], "drinks":[], "extras": []}
            $table->timestamps();

            $table->foreign('order_id')->references('id')->on((new \App\Models\Order())->getTable());
            $table->foreign('item_id')->references('id')->on((new \App\Models\Item())->getTable());
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('order_items');
    }
}
