<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCategories extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('categories', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('vendor_id');
            $table->unsignedBigInteger('parent_id')->nullable();

            foreach (\LaravelLocalization::getSupportedLocales() as $lang => $props) {
                $table->string('name_'.$lang,60);
                $table->string('description_'.$lang,450);
            }

            $table->timestamps();

            $table->foreign('vendor_id')->references('id')->on((new \App\Models\Vendor())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
            $table->foreign('parent_id')->references('id')->on((new \App\Models\Category())->getTable())->cascadeOnUpdate()->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('categories');
    }
}
