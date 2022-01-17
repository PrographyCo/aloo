<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateDriverWalletsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('driver_wallets', function (Blueprint $table) {
            $table->id('car_id');
            $table->float('amount',20,2, true);
            $table->float('reserved',20,2,true)->default(0);
            $table->timestamps();

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
        Schema::dropIfExists('driver_wallets');
    }
}
