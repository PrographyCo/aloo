<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCustomerRatesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('customer_rates', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('customer_id');

            $table->unsignedBigInteger('car_id');
            $table->enum('rate', [1,2,3,4,5]);
            $table->string('message',450);

            $table->timestamps();

            $table->foreign('customer_id')->references('id')->on((new \App\Models\Customer())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
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
        Schema::dropIfExists('customer_rates');
    }
}
