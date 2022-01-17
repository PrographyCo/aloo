<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCarRatesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('car_rates', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('car_id');

            $table->unsignedBigInteger('customer_id');
            $table->enum('rate', [1,2,3,4,5]);
            $table->string('message',450);

            $table->timestamps();

            $table->foreign('car_id')->references('id')->on((new \App\Models\Car())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('customer_id')->references('id')->on((new \App\Models\Customer())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('car_rates');
    }
}
