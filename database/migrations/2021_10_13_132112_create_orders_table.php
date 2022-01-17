<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateOrdersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('orders', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('customer_id');
            $table->unsignedBigInteger('branch_id');
            $table->unsignedBigInteger('place_id');
            $table->enum('status',
                ['unconfirmed', 'confirmed by vendor', 'confirmed by driver', 'driver waiting', 'ready', 'in-delivery',
                    'driver arrived', 'delivered', 'delivery confirmed', 'done', 'cancelled'])->default('unconfirmed');
            $table->float('total_price',20,2,true);
            $table->boolean('isReady')->default(false);

            $table->unsignedBigInteger('car_id')->nullable();
            $table->float('distance',6,4)->nullable();
            $table->time('start_time')->nullable();
            $table->time('deliver_time')->nullable();
            $table->float('delivery_price',20,2,true)->nullable();

            $table->timestamps();

            $table->foreign('customer_id')->references('id')->on((new \App\Models\Customer())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('branch_id')->references('id')->on((new \App\Models\Branch())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('place_id')->references('id')->on((new \App\Models\CustomerPlaces())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('car_id')->references('id')->on((new \App\Models\Car())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('orders');
    }
}
