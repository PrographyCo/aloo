<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateOffersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('offers', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('vendor_id');
            $table->unsignedBigInteger('category_id');
            $table->float('price');
            $table->string('img');

            foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
                $table->string('name_'.$lang,60);
                $table->string('description_'.$lang,450);
            }

            $table->integer('amount');

            $table->json('with')->nullable(); // the things that will go for (with and without) things
            $table->json('without')->nullable(); // the things that will go for (with and without) things
            $table->enum('size',['S','M','B']); // all the sizes and prices of each size
            $table->json('extras')->nullable(); // all the extras things available for this meal
            $table->json('drinks')->nullable(); // all drinks available for this meal

            $table->timestamps();

            $table->foreign('vendor_id')->references('id')->on((new \App\Models\Vendor())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('category_id')->references('id')->on((new \App\Models\Category())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('offers');
    }
}
