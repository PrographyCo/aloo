<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCustomersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('customers', function (Blueprint $table) {
            $table->id();
            $table->string('img', 200)->nullable();
            $table->string('name', 30);
            $table->string('phone',20)->unique();
            $table->string('email',30)->unique()->nullable();
            $table->enum('gender',['male','female','other','prefer not to say']);
            $table->unsignedBigInteger('city_id');
            $table->string('password');
	    $table->enum('phone_type',['android','ios']);

            $table->timestamp('email_verified_at')->nullable();
            $table->timestamp('phone_verified_at')->nullable();

            $table->string('FToken')->nullable();
            $table->boolean('ban')->default(false);
            $table->softDeletes();
            $table->timestamps();

            $table->foreign('city_id')->references('id')->on((new \App\Models\City())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('customers');
    }
}
