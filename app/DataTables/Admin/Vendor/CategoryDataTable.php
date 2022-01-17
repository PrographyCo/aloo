<?php
namespace App\DataTables\Admin\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Category;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class CategoryDataTable extends DataTable
{
    protected $vendor_id;
    public function __construct($vendor_id)
    {
        $this->vendor_id = $vendor_id;
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
            ->editColumn('description_'.Controller::$lang, static function ($query){
                return \WEBHelper::truncate($query->{'description_'.Controller::$lang}, 50);
            })
            ->addColumn('action', static function ($query){
//                todo make plus to show all data about order
                return 'action';
            })->rawColumns([
                'action',
                'description_'.Controller::$lang,
            ]);
    }

    /**
     * Get query source of dataTable.
     *
     * @param \App\User $model
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function query(Category $model)
    {
        return $model
            ->newQuery()->where('vendor_id', $this->vendor_id);
    }

    /**
     * Optional method if you want to use html builder.
     *
     * @return \Yajra\DataTables\Html\Builder
     */
    public function html()
    {
        return $this->builder()
            ->setTableId('category-table')
            ->columns($this->getColumns())
            ->ajax(route('admin.vendor.datatable', [$this->vendor_id, 'category']))
//            ->dom('Bfrtip')
            ->orderBy(0)
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
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.category.name')),
            Column::make('description_'.Controller::$lang)->title(__('web/vendor/datatable.category.desecration')),
            Column::make('created_at')->title(__('web/vendor/datatable.category.created_at')),
//            Column::computed('action')
//                ->exportable(false)
//                ->printable(false)
//                ->width(60)
//                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'category_' . date('YmdHis');
    }
}
