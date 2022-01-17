<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateVendorsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('vendors', function (Blueprint $table) {
            $table->id();
            $table->string('legalName', 50);
            $table->string('brandName', 50);
            $table->string('commercialNo', 100)->unique();
            $table->unsignedBigInteger('city_id');
            $table->string('description', 300);
//            $table->float('minPrice');
            $table->string('email',50)->unique();
            $table->string('phone',20)->unique();
            $table->unsignedBigInteger('supported_vendor_id');
            $table->string('password');

            $table->string('image');

            $table->unsignedBigInteger('bank_id');
            $table->string('bankIBAN', 60)->unique();
            $table->string('bankRecipientName',100);
            $table->string('custid')->nullable();

            $table->string('commercialRecord', 200);
            $table->string('logo', 200);
            $table->string('speech', 200);

            $table->timestamp('email_verified_at')->nullable();
            $table->timestamp('phone_verified_at')->nullable();

            $table->string('FToken')->nullable();
            $table->boolean('ban')->default(false);
            $table->boolean('confirm')->default(true);
            $table->rememberToken();

            $table->timestamps();

            $table->foreign('city_id')->references('id')->on((new \App\Models\City())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('supported_vendor_id')->references('id')->on((new \App\Models\SupportedVendor())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('bank_id')->references('id')->on((new \App\Models\Bank())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('vendors');
    }
}
