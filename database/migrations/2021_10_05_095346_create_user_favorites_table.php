<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUserFavoritesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_favorites', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('customer_id');
            $table->unsignedBigInteger('supported_vendor_id');
            $table->unsignedBigInteger('vendor_id');
            $table->unsignedBigInteger('item_id');
//            $table->json('data'); // the order's data if it was a restaurant (size, with, without, and drinks)
            $table->timestamps();

            $table->foreign('customer_id')->references('id')->on((new \App\Models\Customer())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('vendor_id')->references('id')->on((new \App\Models\Vendor())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('supported_vendor_id')->references('id')->on((new \App\Models\SupportedVendor())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('item_id')->references('id')->on((new \App\Models\Item())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('user_favorites');
    }
}
