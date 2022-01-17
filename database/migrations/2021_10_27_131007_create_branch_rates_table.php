<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateBranchRatesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('branch_rates', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('branch_id');

            $table->unsignedBigInteger('customer_id');
            $table->enum('rate', [1,2,3,4,5]);
            $table->string('message',450);

            $table->timestamps();

            $table->foreign('branch_id')->references('id')->on((new \App\Models\Branch())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
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
        Schema::dropIfExists('branch_rates');
    }
}
