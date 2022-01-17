<?php
namespace App\DataTables\Admin;

use App\Http\Controllers\Controller;
use App\Models\Driver;
use App\Models\Order;
use App\Models\Vendor;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Html\Editor\Editor;
use Yajra\DataTables\Html\Editor\Fields;
use Yajra\DataTables\Services\DataTable;

class DriverDataTable extends DataTable
{
    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('img', static function ($query){
                return '<a href="'.route('admin.driver.show', $query).'"><img src="'.$query->img.'" class="rounded-circle" style="max-height: 64px" alt=""></a>';
            })
            ->editColumn('phone_verified_at', static function ($query){
                return $query->hasVerifiedPhone() ? 'true' : 'false';
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('admin.driver.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<a href="'. route('admin.driver.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>'.
                    '<a href="#" onclick=\'sendMoney('.$query->id.','.($query->wallet() - $query->transactions->sum('amount')).')\' class="rounded mx-1" style="color: green"><i class="ri-money-dollar-circle-fill ri-xl"></i></a>';
            })
            ->addColumn('wallet', static function ($query) {
                return number_format($query->wallet() - $query->transactions->sum('amount'),2,'.','');
            })->rawColumns([
                'action',
                'img',
            ]);
    }

    public function query(Driver $model)
    {
        return $model
            ->newQuery()
            ->where('confirm', 1)
            ->withCount('cars');
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('driver-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('img'),
            Column::make('name')->title(__('web/admin/datatable.driver.name')),
            Column::make('phone')->title(__('web/admin/datatable.driver.phone')),
            Column::make('email')->title(__('web/admin/datatable.driver.email')),
            Column::make('phone_verified_at')->title(__('web/admin/datatable.driver.phoneVerify')),
            Column::make('cars_count')->title(__('web/admin/datatable.driver.cars_count')),
            Column::make('wallet')->title('Money'),
            Column::make('created_at')->title(__('web/admin/datatable.driver.create_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'Driver_' . date('YmdHis');
    }
}
