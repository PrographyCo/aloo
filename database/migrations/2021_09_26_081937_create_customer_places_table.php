<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCustomerPlacesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('customer_places', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('customer_id');
            $table->float('lon',20,15);
            $table->float('lat',20,15);
            $table->string('name',20);
            $table->string('location_name',250)->nullable();
            $table->boolean('isDefault');
            $table->timestamps();

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
        Schema::dropIfExists('customer_places');
    }
}
