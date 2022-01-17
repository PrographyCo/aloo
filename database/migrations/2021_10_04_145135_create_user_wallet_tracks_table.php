<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUserWalletTracksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_wallet_tracks', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('customer_id');
            $table->float('price',20,2,true);
            $table->enum('process', ['in', 'out','reserve']);
            $table->json('direction');  // From/To whom
            $table->string('message',250);
            $table->string('order_type',10);
            $table->string('reason',500);
            $table->boolean('isTransaction')->default(false);
            $table->string('transaction_id')->nullable();
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
        Schema::dropIfExists('user_wallet_tracks');
    }
}
