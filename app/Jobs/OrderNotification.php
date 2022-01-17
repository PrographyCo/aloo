<?php

namespace App\Jobs;

use App\Helpers\APIHelper;
use App\Models\Admin;
use App\Models\Order;
use Carbon\Carbon;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Queue\SerializesModels;

class OrderNotification implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;


    protected Order $order;
    /**
     * Create a new job instance.
     *
     * @return void
     */
    public function __construct(Order $order)
    {
        $this->order = $order;
    }

    /**
     * Execute the job.
     *
     * @return void
     */
    public function handle(): void
    {
        if (!$this->order->car_id){
            foreach (Admin::whereNotNull('FToken')->get() as $admin){
                APIHelper::notification($admin, 'an order is waite driver to deliver it more than 3 min\'s',
                    'order is  :' . route('admin.order.show', $this->order)."\nOrder Id: #".$this->order->id."\n Order Placed In: ".Carbon::make($this->order->created_at)->format('Y-M-D H:i:s'));
            }
        }
    }


}
