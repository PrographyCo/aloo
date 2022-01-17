<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCarsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('cars', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('driver_id');
            $table->string('login_number', 30)->unique();
            $table->string('name', 50);
            $table->string('idNumber', 30);
            $table->string('phone',20)->unique();
            $table->string('email',50)->unique()->nullable();
            $table->enum('gender',['male','female','other']);
            $table->unsignedBigInteger('city_id');
            $table->string('password');

            $table->string('license', 200);
            $table->string('id_img', 200);
            $table->string('phone_type')->nullable();
            $table->string('FToken')->nullable();
            $table->boolean('ban')->default(false);

            $table->timestamps();

            $table->foreign('city_id')->references('id')->on((new \App\Models\City())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('driver_id')->references('id')->on((new \App\Models\Driver())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('cars');
    }
}
