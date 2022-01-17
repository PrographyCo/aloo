<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCartItemsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('cart_items', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('customer_cart_id');
            $table->unsignedBigInteger('item_id')->nullable();
            $table->unsignedBigInteger('offer_id')->nullable();
            $table->unsignedBigInteger('branch_id');
            $table->integer('amount');
            $table->json('data'); //{"size": "", "with": [], "without": [], "drinks":[], "extras": []}
            $table->float('unit_price');
            $table->float('total_price');
            $table->timestamps();

            $table->foreign('customer_cart_id')->references('id')->on((new \App\Models\CustomerCart())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('item_id')->references('id')->on((new \App\Models\Item())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('offer_id')->references('id')->on((new \App\Models\Offer())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('branch_id')->references('id')->on((new \App\Models\Branch())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('cart_items');
    }
}
