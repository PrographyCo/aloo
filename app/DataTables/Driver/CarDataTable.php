<?php
namespace App\DataTables\Driver;

use App\Http\Controllers\Controller;
use App\Models\Car;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class CarDataTable extends DataTable
{
    public function __construct()
    {
        Car::$show = true;
    }

    /**
     * Build DataTable class.
     *
     * @param mixed $query Results from query() method.
     * @return \Yajra\DataTables\DataTableAbstract
     */
    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('driver.car.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<a href="'. route('driver.car.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>'.
                    '<div style="cursor: pointer; display: contents" class="text-blue" onclick="changePassword(`' . route('driver.car.changePassword', $query).'`, `' . $query->name . '`)" class="rounded mx-1"><i class="bi bi-key-fill"></i></div>'.
                    '<a href="#" onclick=\'sendMoney('.$query->id.','.($query->wallet->amount).')\' class="rounded mx-1" style="color: green"><i class="ri-money-dollar-circle-fill ri-xl"></i></a>';
            })->rawColumns([
                'action',
                'img',
            ]);
    }


    public function query(Car $model)
    {
        return $model
            ->newQuery()->where('driver_id', \Auth::id())->with([
                'city:id,name_' . Controller::$lang,
                'wallet'
            ]);
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('car-table')
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
            Column::make('name')->title(__('web/driver/datatable.car.name')),
            Column::make('login_number')->title(__('web/driver/datatable.car.username')),
            Column::make('phone')->title(__('web/driver/datatable.car.phone')),
            Column::make('email')->title(__('web/driver/datatable.car.email')),
            Column::make('gender')->title(__('web/driver/datatable.car.gender')),
            Column::make('city.name_' . Controller::$lang)->title(__('web/driver/datatable.car.city')),
            Column::make('idNumber')->title(__('web/driver/datatable.car.idNumber')),
            Column::make('wallet.amount')->title('Money'),
            Column::make('created_at')->title(__('web/driver/datatable.car.created_at')),
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
        return 'car_' . date('YmdHis');
    }
}
