<?php

namespace App\Console\Commands;

use App\Models\UserPackage;
use Carbon\Carbon;
use Illuminate\Console\Command;

class PackagesExpiryCheck extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'package:expiry';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'This Command Is For Checking For An Expired User Packages';

    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
    }

    /**
     * Execute the console command.
     *
     * @return int
     */
    public function handle()
    {
        UserPackage::whereDate('expiry_date', '<', Carbon::now(\DateTimeZone::AMERICA))->where('status', 'done')->orWhere('orders','<=','0')->update(['status' => 'expired']);
        return Command::SUCCESS;
    }
}
