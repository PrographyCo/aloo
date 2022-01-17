<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateDriversTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('drivers', function (Blueprint $table) {
            $table->id();
            $table->string('img', 200);
            $table->string('name', 50);
            $table->string('phone',20)->unique();
            $table->string('email',50)->unique()->nullable();
            $table->string('password');
            $table->unsignedBigInteger('bank_id');
            $table->string('bankRecipientName',100);
            $table->string('custid')->nullable();

            $table->string('iban', 200);

            $table->timestamp('email_verified_at')->nullable();
            $table->timestamp('phone_verified_at')->nullable();

            $table->string('FToken')->nullable();
            $table->boolean('ban')->default(false);
            $table->boolean('confirm')->default(true);
            $table->rememberToken();

            $table->timestamps();
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
        Schema::dropIfExists('drivers');
    }
}
