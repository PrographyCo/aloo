<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateBranchesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('branches', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('vendor_id');
            $table->unsignedBigInteger('city_id');

            $table->string('name',30);
            $table->boolean('available_status')->default(false);
            $table->string('login_number',30)->unique();
            $table->string('password');
            $table->string('FToken')->nullable();

            $table->string('manager',30);
            $table->string('managerEmail', 50);
            $table->string('managerPhone', 20);
            $table->string('managerPosition',150);

            $table->float('lon',20,15);
            $table->float('lat',20,15);
            $table->string('phone_type')->nullable();
            $table->boolean('ban')->default(false);
            $table->timestamps();

            $table->foreign('vendor_id')->references('id')->on((new \App\Models\Vendor())->getTable())->cascadeOnDelete()->cascadeOnUpdate();
            $table->foreign('city_id')->references('id')->on((new \App\Models\City())->getTable())->cascadeOnDelete()->cascadeOnUpdate();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('branches');
    }
}
